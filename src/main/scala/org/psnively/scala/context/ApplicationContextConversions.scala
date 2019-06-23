/*
 * Copyright 2011-2013 the original author or authors.
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

package org.psnively.scala.context

import org.psnively.scala.beans.factory.BeanFactoryConversions._
import org.psnively.scala.beans.factory.RichListableBeanFactory
import org.springframework.context.ApplicationContext

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
 * A collection of implicit conversions between application contexts and their rich
 * counterpart.
 *
 * @author Arjen Poutsma
 */
object ApplicationContextConversions {

	/**
	 * Implicitly converts a [[org.springframework.context.ApplicationContext]] to a
	 * [[org.psnively.scala.context.RichApplicationContext]].
	 *
	 * @param appContext the application context to be converted
	 * @return the rich application context
	 */
	implicit def toRichApplicationContext(appContext: ApplicationContext): RichApplicationContext =
		new DefaultRichApplicationContext(appContext)

}

private[psnively] class DefaultRichApplicationContext(val appContext: ApplicationContext)
		extends RichApplicationContext {

	private val beanFactory: RichListableBeanFactory = appContext

	def apply[T : ClassTag](): T = beanFactory.apply[T]()

	def apply[T : ClassTag](name: String): T = beanFactory.apply[T](name)

	def beanNamesForType[T : ClassTag](includeNonSingletons: Boolean, allowEagerInit: Boolean): Seq[String] =
		beanFactory.beanNamesForType[T](includeNonSingletons, allowEagerInit)

	def beansOfType[T : ClassTag](includeNonSingletons: Boolean, allowEagerInit: Boolean): Map[String, T] =
		beanFactory.beansOfType[T](includeNonSingletons, allowEagerInit)

}
