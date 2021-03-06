/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.rest.example.tasks.ftest;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.jboss.test.selenium.AbstractTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for the category page (categories.html)
 * @author Jozef Hartinger
 *
 */
public class CategoryPageTest extends AbstractTestCase
{
   private CategoryPage page;

   @BeforeMethod
   public void openCategoryPage()
   {
      if (page == null)
      {
         page = new CategoryPage(selenium, contextPath);
      }
      page.reload();
   }

   @Test
   public void testContent()
   {
      assertTrue(page.isCategoryPresent("School"));
      assertTrue(page.isCategoryPresent("Buy"));
   }

   @Test
   public void testAddingCategory()
   {
      String name = "Home";
      page.newCategory(name);
      assertTrue(page.isCategoryPresent(name));
      page.reload(); // verify changes are stored on the server
      assertTrue(page.isCategoryPresent(name));
      assertTrue(page.goToTaskPage().isCategoryPresent(name));
   }

   @Test
   public void testRemovingCategory()
   {
      String name = "Work";
      assertTrue(page.isCategoryPresent(name));
      page.deleteCategory(name);
      assertFalse(page.isCategoryPresent(name));
      page.reload(); // verify changes are stored on the server
      assertFalse(page.isCategoryPresent(name));
      assertFalse(page.goToTaskPage().isCategoryPresent(name));
      assertFalse(page.goToTaskPage().isTaskPresent(6)); // cascaded delete
   }
}