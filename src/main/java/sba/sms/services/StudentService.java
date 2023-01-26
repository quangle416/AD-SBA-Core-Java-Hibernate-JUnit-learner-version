package sba.sms.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService implements StudentI {

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return null;
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
		Student student = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
		Query<Student> student = session.createQuery("from Student student where email =?")
			
		} catch(HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
			
		} finally {
			session.close();
		return null;
	}

	@Override
	public boolean validateStudent(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Course> getStudentCourses(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
