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

package org.psnively.scala.transaction.support

import org.springframework.transaction.support.{TransactionSynchronization, TransactionSynchronizationManager => DelegateSynchronizationManager}

/**
 * Scala-based convenience wrapper for the Spring
 * [[org.springframework.transaction.support.TransactionSynchronizationManager]], providing pattern matching style
 * callbacks.
 *
 * @author Henryk Konsek
 * @since 1.0
 */
object TransactionSynchronizationManager {

  /**
   * Register partial function delegate for the [[org.springframework.transaction.support.TransactionSynchronization]]
   * callbacks.
   *
   * @param synchronization partial function representing callback to be executed when particular
   * [[org.psnively.scala.transaction.support.SynchronizationEvent]] is fired.
   */
  def registerSynchronization(synchronization: PartialFunction[SynchronizationEvent, Unit]): Unit = {
    def propagateEvent(e: SynchronizationEvent): Unit = {
      if (synchronization.isDefinedAt(e))
        synchronization(e)
    }

    DelegateSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      override def suspend(): Unit = {
        propagateEvent(SuspendEvent)
      }

      override def resume(): Unit = {
        propagateEvent(ResumeEvent)
      }

      override def flush(): Unit = {
        propagateEvent(FlushEvent)
      }

      override def beforeCommit(readOnly: Boolean): Unit = {
        propagateEvent(BeforeCommitEvent(readOnly))
      }

      override def beforeCompletion(): Unit = {
        propagateEvent(BeforeCompletionEvent)
      }

      override def afterCommit(): Unit = {
        propagateEvent(AfterCommitEvent)
      }

      override def afterCompletion(status: Int): Unit = {
        propagateEvent(AfterCompletionEvent(status))
      }
    })
  }

}
