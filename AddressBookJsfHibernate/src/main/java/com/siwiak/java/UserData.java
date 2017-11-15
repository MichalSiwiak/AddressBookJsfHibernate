package com.siwiak.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped
public class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String firstName;
	private String lastName;
	private String eMail;
	private String phoneNumber;
	private int age;
	private double salary;
	List<User> users = new ArrayList<User>();
	User userSelected = new User();
	User userSaved = new User();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
	}

	public User getUserSaved() {
		return userSaved;
	}

	public void setUserSaved(User userSaved) {
		this.userSaved = userSaved;
	}

	@PostConstruct
	public void init() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
		users = query.getResultList();
		entityManager.close();
		entityManagerFactory.close();
		userSelected = new User();
		userSaved = new User();
	}

	public void deleteAction(User user) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		userSelected = entityManager.find(User.class, user.getId());
		entityManager.remove(userSelected);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		init();
	}

	public void editUser() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		userSelected = entityManager.find(User.class, getId());
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public String saveSelected() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		userSaved = entityManager.find(User.class, userSelected.getId());
		userSaved.setFirstName(userSelected.getFirstName());
		userSaved.setLastName(userSelected.getLastName());
		userSaved.seteMail(userSelected.geteMail());
		userSaved.setPhoneNumber(userSelected.getPhoneNumber());
		userSaved.setAge(userSelected.getAge());
		userSaved.setSalary(userSelected.getSalary());
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return "/home.xhtml?faces-redirect=true";
	}

	public String addUser() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.seteMail(eMail);
		user.setPhoneNumber(phoneNumber);
		user.setAge(age);
		user.setSalary(salary);
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return "/home.xhtml?faces-redirect=true";
	}
}
