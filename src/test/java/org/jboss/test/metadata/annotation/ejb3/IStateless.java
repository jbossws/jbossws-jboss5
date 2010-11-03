package org.jboss.test.metadata.annotation.ejb3;

public interface IStateless
{
   int unchecked(int i);

   int checked(int i);

   SomeEntity insertSomeEntity();
   
   SomeEntity readSomeEntity(int key);
   
   void updateSomeEntity(SomeEntity e);
   
   void deleteSomeEntity(SomeEntity e);
   
}
