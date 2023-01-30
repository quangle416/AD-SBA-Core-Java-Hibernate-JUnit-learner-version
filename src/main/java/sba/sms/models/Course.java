package sba.sms.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import lombok.experimental.FieldDefaults;


@NamedQueries({
	@NamedQuery(name = "getAllC", query = "from Course"),
	@NamedQuery(name = "getByC", query = "from Course where id = :id")
})

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@NonNull
	@Column(columnDefinition = "varchar(50)")
	String name;
	@NonNull
	@Column(columnDefinition = "varchar(50)")
	String instructor;
	
	@ToString.Exclude
	@ManyToMany(mappedBy = "courses",
				cascade = {CascadeType.PERSIST, 
							CascadeType.MERGE, 
							CascadeType.REFRESH, 
							CascadeType.DETACH},
				fetch = FetchType.EAGER)

	List<Student> students = new LinkedList<>();

	@Override
	public int hashCode() {
		return Objects.hash(id, instructor, name, students);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return id == other.id && Objects.equals(instructor, other.instructor) && Objects.equals(name, other.name)
				&& Objects.equals(students, other.students);
	}

	public Course(int id, @NonNull String name, @NonNull String instructor) {
		super();
		this.id = id;
		this.name = name;
		this.instructor = instructor;
	}
	
	
	}
	
	
	

