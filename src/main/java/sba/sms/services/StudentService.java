package sba.sms.services;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService implements StudentI {

	@Override
	public List<Student> getAllStudents() {
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Student> sList = new LinkedList<>();
		
		try {
			
			tx = session.beginTransaction();
			
			sList = session.createNamedQuery("getAllS", Student.class).getResultList();
			
			tx.commit();
			
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();
		}
		return sList;
	}

	@Override
	public void createStudent(Student student) {
	
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			tx = session.beginTransaction();
			
			session.merge(student);
			
			tx.commit();
			
		} catch(HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
			
		} finally {
			session.close();
		}
		
	}

	@Override
	public Student getStudentByEmail(String email) {
	
		Transaction tx = null;
		Student s = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			tx = session.beginTransaction();
			Query<Student> q = session.createNamedQuery("getByS", Student.class)
							.setParameter("email", email);
			s = q.getSingleResult();
			tx.commit();
			
		
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		}finally {
			session.close();
		}
		return s;
	}

	@Override
	public boolean validateStudent(String email, String password) {
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean result = false;
		
		try {
			tx = session.beginTransaction();
			
			Query<Student> q = session.createNamedQuery("getByS", Student.class)
										.setParameter("email", email)
										.setParameter("password", password);
			Student s = q.getSingleResult();
			
			if (s.getPassword().equals(password)) {
				result = true;
				
			}else result = false;
		
			tx.commit();
			
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
			
		}finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			Student s = session.get(Student.class, email);
			Course c = session.get(Course.class, courseId);
			
			tx = session.beginTransaction();
			
			s.addCourse(c);
			session.merge(s);
			
			tx.commit();
		
		}catch
		(HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();}
		
	}

	@Override
	public List<Course> getStudentCourses(String email) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Course> sCourses = new LinkedList<>();
		
		try {
			tx = session.beginTransaction();
			
			String q = "Select name From couse";
			sCourses = session.createNativeQuery(q, Course.class)
						.setParameter("email", email)
						.getResultList();

			tx.commit();
			
			
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();
		}
		
	 return sCourses;
		
}
}