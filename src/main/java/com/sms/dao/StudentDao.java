package com.sms.dao;

import com.sms.entity.Student;
import com.sms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Collections;
import java.util.List;

public class StudentDao {
    public List<Student> getStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Student";
            Query<Student> query = session.createQuery(hql, Student.class);
            List<Student> students = query.list();
            return students;

        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public Student findByRollNumber(int rollNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, rollNumber);
            return student;

        } catch (Exception e) {
            System.err.println("Error finding student: " + e.getMessage());
            return null;
        }
    }
    public boolean addStudent(Student student) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }

    }
    public boolean updateStudent(Student student) {Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();}
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteStudent(int rollNumber) {Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, rollNumber);
            if (student == null) {
                System.out.println("No student found with Roll Number: " + rollNumber);
                transaction.rollback();
                return false;
            }
            session.delete(student);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
    public List<Student> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Student WHERE lower(name) LIKE :keyword";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            return query.list();

        } catch (Exception e) {
            System.err.println("Error searching students: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Student> getStudentsSorted(String sortField) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (!sortField.equals("name") && !sortField.equals("age")) {
                sortField = "name";
            }
            String hql = "FROM Student ORDER BY " + sortField + " ASC";
            Query<Student> query = session.createQuery(hql, Student.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error fetching sorted students: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
