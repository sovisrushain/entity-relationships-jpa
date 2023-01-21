# Hibernate | JPA

JDBC is a **low-level** interface, which means that it is **used to invoke SQL commands directly.**

![Screenshot from 2022-07-10 18-53-10.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-10_18-53-10.png)

If we use Native Hibernate then we can not switch to other Jakartha Persistence API(JPA) spec’s implementations (Hibernate, OpenJPA, EclipseLink) in future. Because Native Hibernate has extra features than JPA. 

**ORM - Object Relational Mapping**

A record reside in a table of a database (entity) mapped with a object. 

![Screenshot from 2022-07-10 19-04-13.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-10_19-04-13.png)

## Configuration

**Automatic schema generation**

**hibernate.hbm2ddl.auto:**

`none`

No action will be performed.

`create-only`

Database creation will be generated.

`drop`

Database dropping will be generated.

`create`

Database dropping will be generated followed by database creation.

`create-drop`

Drop the schema and recreate it on SessionFactory startup. Additionally, drop the schema on SessionFactory shutdown.

`validate`

Validate the database schema.

`update`

Update the database schema.

## Bootstrapping

![CqODs.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/CqODs.png)

**JPA Bootstraping**

```java
public class JpaUtil {

    private static final EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory(){

        Properties jpaProp = new Properties();
        try {
            jpaProp.load(JpaUtil.class.getResourceAsStream("/application.properties"));
            return Persistence.createEntityManagerFactory("default", jpaProp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
}
```

**Native Hibernate Bootstraping**

```java
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("application.properties")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
```

## Basic CRUD

**The entity states**

1. New(Transient)
2. Managed
3. Detached
4. Removed

![llll.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/llll.png)

![Screenshot from 2022-07-12 11-50-21.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-12_11-50-21.png)

If new entity resides in the Persistence context then we say that entity is in Managed State.

If that entity already in the DB and not resides in the Context we say that entity is in Detach state.

If entity resides inside the Context then it is seized to the Dirty Checking feature. If a record fetch to the Context by get() or find() from DB, it is seized to the Dirty checking feature. If that record change somehow and whenever commit the transaction then the changes flushes to the DB. 

Similar happens when we save or persist a new entity. It plunge to the Context and whenever the transaction commits, record goes to the DB. 

If we fetch and update a record from DB inside the Context before the commit it’s state change. But if we refresh() it before the commit the DB state and the entity which resides in the Context will synchronize. By default we can refresh entities in the Context. 

If we specify a PRIMARY KEY, Hibernate initially guess this record resides in the DB and treat this as a entity in a detached state. 

By default we can only refresh managed entities, but with Native Hibernate API we can refresh entities which are in the detached state. Other than that if we try to refresh an entity with any other state, it is going to throw an error. 

But if we change in properties to `hibernate.allow_refresh_detached_entity=false` it disables it for the native hibernate also. 

We can use merge() to take back a entity to the Context from detach state or guessing detached state. 

Inside the Context we can not change the PRIMARY KEY (id) of a entity. 

get() and find() are Eager Fetching and load() and getReference() are lazy fetching.

```
LAZY = fetch when needed
EAGER = fetch immediately
```

## Relationships

Owner End cares about the Relationship but Inverse end not.

**Cascade Types**

![Screenshot from 2022-07-12 20-01-22.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-12_20-01-22.png)

**Remember -  M side always unique**

**We do not override toString() in inverse ends**

### 01. One to One Relationship - Total Participation

![Screenshot from 2022-07-12 23-26-16.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-12_23-26-16.png)

**Spouse**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spouse")
public class Spouse implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false, unique = true)
    private Employee employee;
}
```

**Employee**

```java
@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @OneToOne(mappedBy = "employee", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Spouse spouse;

    public Employee(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Employee(String id, String name, String address, Spouse spouse) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.spouse = spouse;
        this.spouse.setEmployee(this);
    }

    public void setSpouse(Spouse spouse) {
        spouse.setEmployee(this);
        this.spouse = spouse;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```

We don’t override toString() in inverse end.

### 02. One to One Relationship - Partial Participation

**When there are no attributes be part of associate entity**

![Screenshot from 2022-07-13 15-06-42.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-13_15-06-42.png)

**Vehicle**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {
    @Id
    private String number;
    @Column(nullable = false)
    private String type;
    @OneToOne
    @JoinTable(name = "vehicle_employee",
            joinColumns = @JoinColumn(name = "number", referencedColumnName = "number", unique = true),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id", unique = true))
    private Employee employee;

    public Vehicle(String number, String type) {
        this.number = number;
        this.type = type;
    }

```

**Employee**

```java
@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @OneToOne(mappedBy = "employee", cascade = {CascadeType.PERSIST})
    private Vehicle vehicle;

    public Employee(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Employee(String id, String name, String address, Vehicle vehicle) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.vehicle = vehicle;
        this.vehicle.setEmployee(this);
    }

    public void setVehicle(Vehicle vehicle) {
        vehicle.setEmployee(this);
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```

**Decompose:** **When there are attributes be part of associate entity**

![Screenshot from 2022-07-15 15-33-40.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-15_15-33-40.png)

**Employee2**

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee2")
public class Employee2 implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
}
```

**Vehicle2**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle2")
public class Vehicle2 implements Serializable {
    @Id
    private String number;
    @Column(nullable = false)
    private String type;
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "vehicle2", cascade = CascadeType.PERSIST)
    private Vehicle2Employee2 vehicle2Employee2;

    public Vehicle2(String number, String type) {
        this.number = number;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicle2{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
```

**Vehicle2Employee2**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle2_employee2")
public class Vehicle2Employee2 implements Serializable {
    @EmbeddedId
    private Vehicle2Employee2PK vehicle2Employee2PK;
    private Date date;
    @Setter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "number", referencedColumnName = "number", insertable = false, updatable = false)
    private Vehicle2 vehicle2;
    @Setter(AccessLevel.NONE)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee2 employee2;

    public Vehicle2Employee2(String number, String id, Date date) {
        this.vehicle2Employee2PK = new Vehicle2Employee2PK(number, id);
        this.date = date;
    }

    public Vehicle2Employee2(Vehicle2Employee2PK vehicle2Employee2PK, Date date) {
        this.vehicle2Employee2PK = vehicle2Employee2PK;
        this.date = date;
    }
}
```

**Vehicle2Employee2PK**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Vehicle2Employee2PK implements Serializable {
    @Column(unique = true)
    private String number;
    @Column(unique = true)
    private String id;
}
```

### 03. One to Many Relationship - Total Participation

![Screenshot from 2022-07-15 20-35-26.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-15_20-35-26.png)

**Order**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private Date date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
}
```

**Customer**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @OneToMany(mappedBy = "customer")
    private List<Order> orderList;

    public Customer(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public void setOrderList(List<Order> orderList) {
        orderList.forEach(order -> order.setCustomer(this));
        this.orderList = orderList;
    }

    public void addOrder(Order order) {
        order.setCustomer(this);
        this.orderList.add(order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```

### 04. One To Many Relationship - Partial Participation

**When there are no attributes be part of associate entity**

![Screenshot from 2022-07-16 00-08-43.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-16_00-08-43.png)

**Order2**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order2")
public class Order2 implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private Date date;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "customer2_order2",
                        joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
                        inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    private Customer2 customer2;

    public Order2(String id, Date date) {
        this.id = id;
        this.date = date;
    }
}
```

**Customer2**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer2")
public class Customer2 implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @OneToMany(mappedBy = "customer2")
    private List<Order2> order2List;

    public Customer2(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public void setOrder2List(List<Order2> order2List) {
        order2List.forEach(order2 -> order2.setCustomer2(this));
        this.order2List = order2List;
    }

    public void addOrder(Order2 order2) {
        order2.setCustomer2(this);
        this.getOrder2List().add(order2);
    }

    public void removeOrder(Order2 order2) {
        this.order2List.remove(order2);
        order2.setCustomer2(null);
    }

    @Override
    public String toString() {
        return "Customer2{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```

**Decompose:** **When there are attributes be part of associate entity**

![Screenshot from 2022-07-17 15-49-39.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-17_15-49-39.png)

**ClassStudent**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class_student")
public class ClassStudent implements Serializable {
    @EmbeddedId
    private ClassStudentPK classStudentPK;
    @Column(nullable = false)
    private Date date;
    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "class_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Class classRef;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Student student;

    public ClassStudent(ClassStudentPK classStudentPK, Date date) {
        this.classStudentPK = classStudentPK;
        this.date = date;
    }

    public ClassStudent(String classId, String studentId, Date date) {
        this.classStudentPK = new ClassStudentPK(classId, studentId);
        this.date = date;
    }

    public ClassStudent(ClassStudentPK classStudentPK, Date date, Student student) {
        this.classStudentPK = classStudentPK;
        this.date = date;
        this.student = student;
    }
}
```

**ClassStudentPK**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ClassStudentPK implements Serializable {
    @Column(name = "class_id")
    private String classId;
    @Column(name = "student_id", unique = true)
    private String studentId;
}
```

**Class**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class")
public class Class implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String description;
    @OneToMany(mappedBy = "classRef", cascade = CascadeType.PERSIST)
    List<ClassStudent> classStudentList;

    public Class(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
```

**Student**

```java
@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @OneToOne(mappedBy = "student", cascade = CascadeType.PERSIST)
    private ClassStudent classStudent;

    public Student(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Student(String id, String name, String address, ClassStudent classStudent) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.classStudent = classStudent;
        this.classStudent.getClassStudentPK().setStudentId(this.id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```

### 05. Many To many Relationship

**When there are no attributes be part of associate entity**

![Screenshot from 2022-07-17 17-38-37.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-17_17-38-37.png)

**Actor**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actor")
public class Actor implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(name = "actor_movie",
                joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<Movie> movieList = new HashSet<>();

    public Actor(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

**Movie**

```java
@Data
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "movieList", cascade = CascadeType.PERSIST)
    private List<Actor> actorList;

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Movie(String id, String name, List<Actor> actorList) {
        this.id = id;
        this.name = name;
        this.actorList = actorList;
        this.actorList.forEach(actor -> actor.getMovieList().add(this));
    }

    public void addActor(Actor actor) {
        actorList.add(actor);
        actor.getMovieList().add(this);
    }

    public void removeActor(Actor actor) {
        actor.getMovieList().remove(this);
        this.actorList.remove(actor);
    }

    public void setActorList(List<Actor> actorList) {
        actorList.forEach(actor -> actor.getMovieList().add(this));
        this.actorList = actorList;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
```

**Decompose:** **When there are attributes be part of associate entity**

![Screenshot from 2022-07-18 11-57-50.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-18_11-57-50.png)

**Bill**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private Date date;
    @OneToMany(mappedBy = "bill", cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<BillDetail> billDetailList;

    public Bill(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", date=" + date +
                '}';
    }
}
```

**Item**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Id
    private String code;
    @Column(nullable = false)
    private String description;
    @Column(name = "qty_on_hand",nullable = false)
    private int qtyOnHand;
    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;
    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST})
    private List<BillDetail> billDetailList;

    public Item(String code, String description, int qtyOnHand, BigDecimal unitPrice) {
        this.code = code;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
```

**BilDetailPK**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BillDetailPK implements Serializable {
    @Column(name = "item_code")
    private String itemCode;
    @Column(name = "bill_id")
    private String billId;
}
```

**BillDetail**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill_detail")
public class BillDetail implements Serializable {
    @EmbeddedId
    private BillDetailPK billDetailPK;
    @Column(nullable = false)
    private int qty;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_code", referencedColumnName = "code", insertable = false, updatable = false)
    private Item item;
    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bill_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Bill bill;

    public BillDetail(String itemCode, String billId, int qty, BigDecimal unitPrice) {
        this.billDetailPK = new BillDetailPK(itemCode, billId);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public BillDetail(BillDetailPK billDetailPK, int qty, BigDecimal unitPrice) {
        this.billDetailPK = billDetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
```

## Queries

![Screenshot from 2022-07-18 12-44-02.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-18_12-44-02.png)

JPQL

- SELECT
- UPDATE
- DELETE

HQL

- SELECT
- UPDATE
- DELETE
- INSERT INTO …SELECT

![Screenshot from 2022-07-18 12-49-41.png](Hibernate%20JPA%208f712c92967e40239e0eed24e5ddc8f1/Screenshot_from_2022-07-18_12-49-41.png)

**Retrieve multiple records**

```java
NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM customer");
nativeQuery.addEntity(Customer.class);
List<Customer> list = nativeQuery.list();

/* Native Hibernate API */
NativeQuery<Customer> nativeQuery = session.createNativeQuery("SELECT * FROM customer", Customer.class);
List<Customer> list = nativeQuery.list();

/* JPA API */
Query nativeQueryInJPA = session.createNativeQuery("SELECT * FROM customer", Customer.class);
List<Customer> list = nativeQueryInJPA.getResultList();
```

**Retrieve a single Record**

```java
NativeQuery query1 = session.createNativeQuery("SELECT * FROM customer WHERE id='C001'");
Object[] customer1 = (Object[]) query1.uniqueResult();

/* Native Hibernate API (NativeQuery<T>), JPA API (Query) */
NativeQuery<Customer> query2 = session.createNativeQuery("SELECT * FROM customer WHERE id='C001'", Customer.class);
Customer customer2 = query2.uniqueResult(); // getSingleResult()

/* Native Hibernate API */
NativeQuery query3 = session.createNativeQuery("SELECT * FROM customer WHERE id='C001'");
query3.addEntity(Customer.class);
Customer customer3 = (Customer) query3.getSingleResult();

/* Native Hibernate API */
NativeQuery<Customer> query4 = session.createNativeQuery("SELECT * FROM customer WHERE id='C001'").addEntity(Customer.class);
Customer customer4 = query4.getSingleResult();
```

**Parameterized Query**

positional parameters `?1`

named parameters `:id`

```java
NativeQuery<Customer> query1 = session.createNativeQuery("SELECT * FROM customer WHERE id=?1", Customer.class);
query1.setParameter(1, "C001");
Customer customer1 = query1.getSingleResult();

NativeQuery<Customer> query2 = session.createNativeQuery("SELECT * FROM customer WHERE id=:abc", Customer.class);
query2.setParameter("abc", "C002" );
Customer customer2 = query2.uniqueResult();
```

**Insert Records**

```java
NativeQuery query = session.createNativeQuery("INSERT INTO customer (id, name, address) VALUES (:id, :name, :address)");
query.setParameter("id", "C004");
query.setParameter("name", "Aruni");
query.setParameter("address", "Matara");
int affectedRows = query.executeUpdate();
```

**Update Records**

```java
int affectedRows = session.createQuery("UPDATE com.cisco.entity.Customer C SET C.name=?1 WHERE C.id=?2")
.setParameter(1, "Aruni Dissanayake")
.setParameter(2, "C004")
.executeUpdate();
```

**Delete Records**

```java
int affectedRows = session.createQuery("DELETE FROM com.cisco.entity.Customer c WHERE c.id=?1")
.setParameter(1, "C003")
.executeUpdate();
```

**Insert a Record**

```java
// JPQL
List<Customer> customers = session.createQuery("SELECT c FROM Customer c", Customer.class).list();

// HQL
List<Customer> customers2 = session.createQuery("FROM Customer c", Customer.class).list();

List<String> names = session.createQuery("SELECT c.name FROM Customer c", String.class).list();
            
List<Detail> details = session.createQuery("SELECT new lk.ijse.dep7.Detail(c.name, c.address) FROM Customer c").list();
         
List<Detail> details2 = session.createNativeQuery("SELECT c.name, c.address FROM customer c")
                    .setResultTransformer(Transformers.aliasToBean(Detail.class)).list();

```

**Insert Into Select**

```java
int affectedRows = session.createQuery("INSERT INTO Customer (id, name, address) SELECT s.id, s.name, s.address FROM Student s").executeUpdate();
```

**Joins**

```java
// HQL
List<CustomEntity> employeeDetailList = session.createNativeQuery("SELECT e.id, e.name, e.address, s.name as spouseName FROM employee e INNER JOIN spouse s on e.id = s.employee_id")
                            .setResultTransformer(Transformers.aliasToBean(CustomEntity.class)).list();

// JPQL
List<CustomEntity> employeeDetailList = session.createQuery("SELECT new lk.ijse.dep7.entity.CustomEntity(e.id, e.name, e.address, s.name) FROM Spouse s INNER JOIN s.employee e").list()
```