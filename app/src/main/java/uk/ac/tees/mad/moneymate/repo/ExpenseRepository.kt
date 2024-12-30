package uk.ac.tees.mad.moneymate.repo

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.moneymate.database.Expense
import uk.ac.tees.mad.moneymate.database.ExpenseDao
import uk.ac.tees.mad.moneymate.firestore.FirestoreDataSource
import uk.ac.tees.mad.moneymate.firestore.StorageDataSource
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val storageDataSource: StorageDataSource
) {
    fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }

    suspend fun addExpense(expense: Expense) {
        val expenseWithAttachment = if (expense.attachment != null) {
            val attachmentUrl = storageDataSource.uploadAttachment(expense.attachment)
            expense.copy(attachment = attachmentUrl)
        } else {
            expense
        }
        expenseDao.insertExpense(expenseWithAttachment)
        firestoreDataSource.saveExpenseToFirestore(expenseWithAttachment)
    }

    suspend fun updateExpense(expense: Expense) {
        val expenseWithAttachment = if (expense.attachment != null && !expense.attachment.startsWith("http")) {
            val attachmentUrl = storageDataSource.uploadAttachment(expense.attachment)
            expense.copy(attachment = attachmentUrl)
        } else {
            expense
        }
        expenseDao.updateExpense(expenseWithAttachment)
        firestoreDataSource.updateExpenseInFirestore(expenseWithAttachment)
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
        firestoreDataSource.deleteExpenseFromFirestore(expense.id)
        expense.attachment?.let { storageDataSource.deleteAttachment(it) }
    }
}