/*package com.arcor.util;

import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Types; 
import java.sql.Blob; 

import com.arcor.servlet.WebContext;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.UserType;

public class BinaryBlobType implements UserType 
{ 
  public int[] sqlTypes() 
  { 
    return new int[] { Types.BLOB }; 
  }

  public Class returnedClass() 
  { 
    return byte[].class; 
  } 

  public boolean equals(Object x, Object y) 
  { 
    return (x == y) 
      || (x != null 
        && y != null 
        && java.util.Arrays.equals((byte[]) x, (byte[]) y)); 
  } 

  public Object nullSafeGet(ResultSet rs, String[] names, Object owner) 
  throws HibernateException, SQLException 
  { 
    Blob blob = rs.getBlob(names[0]); 
    return blob.getBytes(1, (int) blob.length()); 
  } 

  public void nullSafeSet(PreparedStatement st, Object value, int index)
  throws HibernateException, SQLException
{
	  oracle.sql.BLOB blob =
			oracle.sql.BLOB.createTemporary(st.getConnection(),
				false,
				oracle.sql.BLOB.DURATION_SESSION);  OutputStream t_out = null;

  t_blob.open(BLOB.MODE_READWRITE);

  t_out = t_blob.getBinaryOutputStream();

  try
  {
    t_out.write((byte[]) value);
    t_out.flush();
    t_out.close();
  }
  catch (IOException e)
  {
    throw new SQLException("failed write to blob" + e.getMessage());
  }

  t_blob.close();

  st.setBlob(index, t_blob);
}

  public Object deepCopy(Object value) 
  { 
    if (value == null) return null; 

    byte[] bytes = (byte[]) value; 
    byte[] result = new byte[bytes.length]; 
    System.arraycopy(bytes, 0, result, 0, bytes.length); 

    return result; 
  } 

  public boolean isMutable() 
  { 
    return true; 
  }
}*/