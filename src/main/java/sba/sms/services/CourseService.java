package sba.sms.services;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

public class CourseService implements CourseI {

	@Override
	public void createCourse(Course course) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			tx = session.beginTransaction();
			
			session.merge(course);
			
			tx.commit();
		
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();
		}
	}

	@Override
	public Course getCourseById(int courseId) {
		Transaction tx = null;
		Course c = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			tx = session.beginTransaction();
			
			c = session.createNamedQuery("getByC", Course.class)
						.setParameter("id", courseId)
						.getSingleResult();
			
			tx.commit();
		
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		}finally {
			session.close();
		}
		return c;
	}

	@Override
	public List<Course> getAllCourses() {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Course> cList = new LinkedList<>();
		
		try {
			tx = session.beginTransaction();
			
			cList = session.createNamedQuery("getAllC", Course.class)
										.getResultList();
			
			tx.commit();
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			
			session.close();
		}
		
		;
		return cList;
	}

}
