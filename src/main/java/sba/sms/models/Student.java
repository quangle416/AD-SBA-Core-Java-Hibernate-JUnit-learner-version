package sba.sms.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;

@NamedQueries({
	@NamedQuery(name = "getAll", query = "from Employee"),
	@NamedQuery(name = "getBy", query = "from Student where email = :email")
})

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults( level = AccessLevel.PRIVATE)
@Table (name = "student")
public class Student {
		
		@Id
		@Column(columnDefinition = "varchar(50)", name = "email")
		String email;
		@NonNull
		@Column(columnDefinition = "varchar(50)", name = "name")
		String name;
		@NonNull
		@Exclude
		@Column(columnDefinition = "varchar(50)", name = "password")
		String password;
		
		@ToString.Exclude
		@ManyToMany(cascade = {CascadeType.PERSIST, 
								CascadeType.MERGE, 
								CascadeType.REFRESH, 
								CascadeType.DETACH}, 
					fetch = FetchType.EAGER)
		@JoinTable(name = "student_courses",
		joinColumns = @JoinColumn(email = "student_email"),
		inverseJoinColumns = @JoinColumn(courses = "courses_id"))
		
		List<Course> courses = new ArrayList<>();

		@Override
		public int hashCode() {
			return Objects.hash(courses, email, name, password);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Student other = (Student) obj;
			return Objects.equals(courses, other.courses) && Objects.equals(email, other.email)
					&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
		}
		
		public void addCourse(Course c) {
			courses.add(c);
			c.getStudents().add(this);
		}

		
}
