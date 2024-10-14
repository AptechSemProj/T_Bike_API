package se.pj.tbike.api.core.user;

import static java.util.Objects.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.order.Order;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "users", indexes = {
		@Index( name = "idx_account", columnList = "username, password" )
} )
public class User
		extends IdentifiedEntity<User> {

	//*************** BASIC ******************//

	@Column( nullable = false )
	@Enumerated( EnumType.ORDINAL )
	@Convert( converter = Role.Converter.class )
	private Role role;

	@Column( length = 100, nullable = false, unique = true )
	private String username;

	@Column( columnDefinition = "CHAR(60)", nullable = false )
	private String password;

	@Column( length = 100, nullable = false )
	private String name;

	@Column( name = "phone_number", columnDefinition = "CHAR(10)",
			nullable = false )
	private String phoneNumber;

	@Column( name = "avatar_image", columnDefinition = "TEXT" )
	private String avatarImage;

	//*************** RELATIONSHIPS ******************//

	@OneToMany( mappedBy = "user", fetch = FetchType.LAZY )
	private List<Order> orders = new ArrayList<>();

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof User that ) ) return false;
		if ( !( getId() == that.getId() &&
				Objects.equals( username, that.username ) &&
				Objects.equals( password, that.password ) &&
				Objects.equals( name, that.name ) &&
				Objects.equals( phoneNumber, that.phoneNumber ) &&
				Objects.equals( avatarImage, that.avatarImage ) ) )
			return false;
		if ( orders != null ) {
			if ( that.orders == null ) return false;
			int s = orders.size();
			if ( s != that.orders.size() ) return false;
			long id1, id2;
			for ( int i = 0; i < s; i++ ) {
				id1 = orders.get( i ).getId();
				id2 = that.orders.get( i ).getId();
				if ( id1 != id2 ) return false;
			}
			return true;
		}
		return that.orders == null;
	}

	@Override
	public int hashCode() {
		int hashed = hash( getId(), username, password,
				name, phoneNumber, avatarImage );
		if ( orders == null ) return hashed;
		int s = orders.size();
		long[] ids = new long[s];
		for ( int i = 0; i < s; i++ )
			ids[i] = orders.get( i ).getId();
		return hash( hashed, Arrays.hashCode( ids ) );
	}


	@Getter
	@AllArgsConstructor
	public enum Role {
		USER( 1, "External user" ),
		ADMIN( 2, "Internal user" ),
		;

		private final int id;
		private final String roleName;

		public static Role valueOf( int id ) {
			return Stream.of( Role.values() )
					.filter( r -> r.id == id )
					.findFirst()
					.orElseThrow( IllegalAccessError::new );
		}

		private static class Converter
				implements AttributeConverter<Role, Integer> {

			@Override
			public Integer convertToDatabaseColumn( Role role ) {
				return role.id;
			}

			@Override
			public Role convertToEntityAttribute( Integer id ) {
				if ( id == null )
					throw new NullPointerException();
				return Role.valueOf( id );
			}
		}
	}
}
