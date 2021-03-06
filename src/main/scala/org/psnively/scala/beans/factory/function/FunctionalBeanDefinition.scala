/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.psnively.scala.beans.factory.function

import org.springframework.beans.factory.config.BeanDefinition

/**
 * Extended [[org.springframework.beans.factory.config.BeanDefinition]]
 * interface that exposes a bean creation function.
 *
 * @author Arjen Poutsma
 * @see FunctionalGenericBeanDefinition
 */
trait FunctionalBeanDefinition[T] extends BeanDefinition {

	/**
	 * Returns the bean creation function.
	 * @return the bean creation function
	 */
	def beanCreationFunction: () => T
}

