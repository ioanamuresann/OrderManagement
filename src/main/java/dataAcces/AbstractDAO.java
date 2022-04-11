package dataAcces;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

import javax.swing.table.DefaultTableModel;

/**
 * Clasa abstracta care sta la baza reflexiei
 * @param <T> T poate fi Client,Product sau Order
 */
public class AbstractDAO<T> {
    /**
     * Atributele clasei
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     *
     * @param field Numele coloanei de unde se va face selectia
     * @return Stringul reprezentat de instructiunea sql de generare a operatiei de SELECT
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     *
     * @return Stringul reprezentat de instructiunea sql de generare a operatiei de INSERT
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" VALUES ( ");
        for(Field f:type.getDeclaredFields()){
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }
    /**
     *
     * @return Stringul reprezentat de instructiunea sql de generare a operatiei de UPDATE
     */
    private String createUpdateQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" SET ");
        for(Field f : type.getDeclaredFields()){
            sb.append(f.getName() + "=?,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" WHERE id=?");
        return sb.toString();
    }
    /**
     * @return Stringul reprezentat de instructiunea sql de generare a operatiei de DELETE
     */
    private String createDeleteQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Cauta obiectul T care are id-ul id
     * @param id Un intreg dupa care se va face cautarea
     * @return Un obiect T gasit dupa id
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
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
     * Insereaza in lista un obiect de tipul T
     * @param t Un obiect de clasa T
     * @return Un obiect de clasa T
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i=1;
          Field f[]=t.getClass().getDeclaredFields();
          for(int j=0;j<f.length;j++)
            {
                f[j].setAccessible(true);
                Object value=f[j].get(t);
                statement.setObject(i,value);
                i++;
                f[j].setAccessible(false);
            }
            statement.executeUpdate();

        } catch (SQLException  e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {

            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Editeaza  un obiect de tipul T
     * @param t Un obiect de clasa T
     * @return Un obiect de clasa T
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i=1;
            for(Field f:t.getClass().getDeclaredFields())
            {
                f.setAccessible(true);
                Object value=f.get(t);
                statement.setObject(i,value);
                i++;
                f.setAccessible(false);
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Sterge din lista un obiect de tipul T
     * @param t Un obiect de clasa T
     * @return Un obiect de clasa T
     */
    public T delete(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        Field f = t.getClass().getDeclaredFields()[0];
        f.setAccessible(true);
        String query = createDeleteQuery(f.getName());
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,(int) f.get(t));
        } catch (SQLException | IllegalAccessException throwables) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: Delete " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Gaseste toate fieldurile din lista de obiecte de tip T si construieste o matrice din ele
     * @param T Un obiect de tipul T
     * @param list O lista de obiecte de tipul T
     * @return O matrice de field-uri
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object[][] fields(Object T, List<T> list) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        DefaultTableModel table=new DefaultTableModel();
        Class<T> types=(Class <T>) Client.class;
        int nrOfColumns =type.getDeclaredFields().length;
        String [] columnsName=new String[nrOfColumns];
        Object [][] data=new Object[list.size()][nrOfColumns];
        int i=0; int k=0;
        int j=0;
        for(Field f:type.getDeclaredFields())
        {
            columnsName[i]=f.getName();
            i++;
        }
        for(T element:list)
        {
            j=0;
            for(Field field:type.getDeclaredFields())
            {
                PropertyDescriptor propertyDescriptor=new PropertyDescriptor(field.getName(),type);
                Object value=propertyDescriptor.getReadMethod().invoke(element);
             data[k][j]=value;
                j++;
            }
            k++;
        }
        return data;
    }

}
