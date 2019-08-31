package co.id.kadaluarsa.gojekassignment.di

import io.reactivex.subjects.PublishSubject

object AppProvider {
    private val subject = PublishSubject.create<Unit>()
    fun provideRetrySubject() : PublishSubject<Unit> = subject
}