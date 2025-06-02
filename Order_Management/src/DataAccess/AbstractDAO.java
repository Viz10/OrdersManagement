package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * The class is responsible for performing database operations related to generic types of objects
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    ///////////////////////// REFLECTION
    private Constructor getBaseConstructor() {
        Constructor[] ctors = type.getDeclaredConstructors(); /// get constructor list
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0) /// use the base constr
                break;
        }
        return ctor;
    }
    private List<T> createObjects(ResultSet resultSet,Integer noOfColumns) {
        List<T> list = new ArrayList<>();
        Constructor ctor = getBaseConstructor();
        try {
            int cnt=0;
            while (resultSet.next()) { /// take each line
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance(); /// for each row create a new obj
                for (Field field : type.getDeclaredFields()) {
                    if(cnt==0) {
                        noOfColumns++;
                    }
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value); /// use setter like method
                }
                list.add(instance);
                cnt++;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Retrieves all declared field values and names from the specified object using reflection.
     *
     * @param object the object from which to retrieve the properties
     * @param values the list to which field values will be added
     * @param names  the list to which field names will be added
     */
    public void retrieveProperties(Object object, ArrayList<Object> values, ArrayList<String> names) {

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                values.add(field.get(object));
                names.add(field.getName());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    ///////////////////////// QUERIES
    private String updateQuery(List<String> columnToUpdate , String whereColumn){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName()); //// TABLE NAME
        sb.append(" SET ");
        for(int i=0;i<columnToUpdate.size();i++) {
            sb.append(columnToUpdate.get(i)).append(" = ?");
            if (i < columnToUpdate.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE ");
        sb.append(whereColumn);
        sb.append(" = ?");
        return sb.toString();
    }
    private String findByFieldQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName()); //// TABLE NAME
        sb.append(" WHERE " + field + " =?"); //// WHAT THE SELECTION IS BASED ON
        return sb.toString();
    }
    private String findByFieldsQuery(ArrayList<String> fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        int cnt=0;
        for(String field : fields) {
            sb.append(field).append(" =?");
            if(cnt<fields.size()-1) {
                sb.append(" AND ");
            }
            cnt++;
        }
        return sb.toString();
    }
    private String findAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append(type.getSimpleName()); //// TABLE NAME
        return sb.toString();
    }
    private String insertQuery(ArrayList<String> names,int nr_of_fields) {
        StringBuilder sb = new StringBuilder();
        /// start from 1 to skip id part , that will be generated by the DB
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName()); //// TABLE NAME
        sb.append(" (");
        for(int i = 1; i < names.size()-1; i++) {
            sb.append(names.get(i));
            sb.append(", ");
        }
        sb.append(names.getLast());

        sb.append(") VALUES (");
        for(int i=1;i<nr_of_fields-1;i++) {
            sb.append("?, ");
        }
        sb.append("?)");
        return sb.toString();
    }
    private String findIdFromSomethingQuery(String typeName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" id ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + typeName + " =?");
        return sb.toString();
    }
    private String deleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("Delete ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName()); //// TABLE NAME
        sb.append(" WHERE id =?"); //// WHAT THE SELECTION IS BASED ON
        return sb.toString();
    }

    ////////////////////////// SELECTS
    /**
     * Retrieves all objects of type T from the database.
     *
     * @param noOfColumns the number of columns expected in the result set records
     * @return a list of objects of type T or null if no records were found
     */
    public List<T> findAll(Integer noOfColumns) {
        Connection connection;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = findAllQuery();
        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<T> res = createObjects(resultSet, noOfColumns);
            if (res.isEmpty()) {
                return null;
            }
            return res;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }
        return null;
    }

    /**
     * Finds and returns the object of type T with the specified id.
     *
     * @param id the identifier of the object to retrieve
     * @return the object with the given id or null if not found
     */
    public T findById(int id) {
        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = findByFieldQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> res = createObjects(resultSet, 0);
            if (res.isEmpty()) {
                return null;
            }
            return res.getFirst();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }
        return null;
    }

    /**
     * Finds and returns an object of type T that matches the fields except id ,of the given object.
     *
     * @param t the object containing the criteria for the search
     * @return the matching object if found, or null otherwise
     */
    public T findByFields(T t) {
        /// don't consider id
        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        retrieveProperties(t, values, names);

        names.removeFirst(); /// remove id (set to 0)
        values.removeFirst();

        String query = findByFieldsQuery(names);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            resultSet = statement.executeQuery();

            List<T> res = createObjects(resultSet, 0);
            if (res.isEmpty()) {
                return null;
            }
            return res.getFirst();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }
        return null;
    }

    /**
     * Finds and returns an id from the database based on a specified column name and value.
     *
     * @param columnName the column name to filter by
     * @param value      the value to match in the specified column
     * @return the found id or -1 if no matching id is found
     */
    public int findIdFromSomething(String columnName, String value) {

        int id = -1;

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = findIdFromSomethingQuery(columnName);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, value);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("id"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
        }
        return id;
    }

////////////////////////////// INSERTS pass object , use reflection , returns copy of added obj
    /**
     * Inserts a new object of type T into the database.
     *
     * <p>This method uses reflection to extract field values (except id) from the given object and construct an insert query.</p>
     *
     * @param t the object to be inserted into the database
     * @return the newly inserted object as retrieved from the database
     * @throws RuntimeException if a database error occurs during insertion or retrieval
     */
    public T insert(T t) {

        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        retrieveProperties(t, values, names);
        String query = insertQuery(names, values.size());

        try (PreparedStatement ps = ConnectionFactory.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < values.size() - 1; i++) {
                ps.setObject(i + 1, values.get(i + 1)); /// set "?"
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting object failed, no rows affected.");
            }

            /// from here on is confirmed its insertion

            int insertedId = 0;
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
            }

            /// return newly generated line as an object

            T result = findByFields(t);
            if (result == null) {
                boolean deleted = delete(insertedId);
                if (!deleted) {
                    throw new SQLException("Failed deleting newly created object.");
                }
                throw new SQLException("Failed retrieving newly created object.");
            }

            /// confirmed its retrieval

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

////////////////////////////// DELETES
    /**
     * Deletes an object of type T from the database based on the provided id.
     *
     * @param id the id of the object to be deleted
     * @return {@code true} if the deletion was successful {@code false} otherwise
     */
    public boolean delete(int id) {

        String query = deleteQuery();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(query)) {

            ps.setObject(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed , no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

////////////////////////////// UPDATES
    /**
     * Updates the object of type T in the database.
     *
     * <p>The object passed in must have a valid id, which is used to identify the record to update.</p>
     *
     * @param t the updated object of type T (with id intact)
     * @return the updated object if the update was successful {@code null} otherwise
     */
    public T update(T t) {

        /// t is the updated object , with the id intact and maybe other fields modified

        ArrayList<Object> values = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        retrieveProperties(t, values, names);

        Object id = values.getFirst(); ///// Extract ID value
        names.removeFirst();    ///// Remove id from column updates
        values.removeFirst();

        String updateQuery = updateQuery(names, "id");

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(updateQuery)) {

            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }

            ps.setObject(values.size() + 1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed , no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return t;
    }

/////////////////////////////// TABLE HELPER

    /**
     * Constructs a table representation of objects of type T for display purposes.
     *
     * @return a {@link Map.Entry} with a two-dimensional Object array (table data) as the key and an {@link ArrayList}
     *         of column names as the value
     */
    public Map.Entry<Object[][], ArrayList<String>> tableHelper() {

        int i = 0;
        Integer noOfColumns = 0;

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        List<T> objects = findAll(noOfColumns); /// T is the type of obj from whom BLL is called

        if (objects == null || objects.isEmpty()) { /// empty table
        /// must create empty instance
            T instance;
            try {
                instance = (T) getBaseConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            retrieveProperties(instance, values, names);
            Object[][] result = new Object[0][noOfColumns]; /// empty JTable , only column names needed
            return new AbstractMap.SimpleEntry<>(result, names);
        }

        Object[][] result = new Object[objects.size()][noOfColumns];

        for (T object : objects) {

            names.clear();
            values.clear();
            retrieveProperties(object, values, names);
            result[i] = values.toArray();
            i++;
        }

        return new AbstractMap.SimpleEntry<>(result, names);
    }

}
