package org.sdmlib.models.classes.logic;

import org.sdmlib.models.classes.Association;
import org.sdmlib.models.classes.Clazz;

public class GenAssociation extends Generator<Association>
{
   public GenAssociation generate(String rootDir, String helperDir)
   {
      // open source class and get or insert role implementation
      GenClassModel generator = model.getSource().getClazz().getClassModel().getGenerator();
      GenRole sourceGenRole = generator.getOrCreate(model.getSource());
      sourceGenRole.generate(rootDir, helperDir, model.getTarget());
      
      // also for subclasses
      for (Clazz kidClass : model.getSource().getClazz().getKidClazzes())
      {
         boolean needsImplementation = kidClass.getInterfaces().contains(model.getSource().getClazz());
         sourceGenRole.generate(kidClass, rootDir, helperDir, model.getTarget(), ! needsImplementation);
      }
      GenRole targetGenRole = generator.getOrCreate(model.getTarget());
      // open target class and get or insert role implementation
      targetGenRole.generate(rootDir, helperDir, model.getSource());

      // also for subclasses
      for (Clazz kidClass : model.getTarget().getClazz().getKidClazzes())
      {
         boolean needsImplementation = kidClass.getInterfaces().contains(model.getTarget().getClazz());
         targetGenRole.generate(kidClass, rootDir, helperDir, model.getSource(), ! needsImplementation);
      }
      return this;
   } 
}
