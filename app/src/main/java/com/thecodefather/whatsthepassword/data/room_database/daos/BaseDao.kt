package com.thecodefather.whatsthepassword.data.room_database.daos

import androidx.room.*
import com.thecodefather.whatsthepassword.internal.extensions.debug
import com.thecodefather.whatsthepassword.internal.extensions.fastLog

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * Created by Hussein Yassine on 05, March, 2019.
 *
 */

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(objs: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(obj:T){
        Completable
            .fromAction { insert(obj) }
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    debug("onComplete")
                }

                override fun onError(e: Throwable) {
                    e.message?.let { debug(it) }
                }
            })
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(objs: List<T>){
        Completable
            .fromAction { insertList(objs) }
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    debug("onComplete")
                }

                override fun onError(e: Throwable) {
                    e.message?.let { debug(it) }
                }
            })
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(obj:T){
        Completable
            .fromAction { update(obj) }
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    debug("onComplete")
                }

                override fun onError(e: Throwable) {
                    e.message?.let { debug(it) }
                }
            })
    }

    @Delete
    fun deleteItem(obj:T){
        Completable
            .fromAction { delete(obj) }
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    debug("onComplete")
                }

                override fun onError(e: Throwable) {
                    e.message?.let { debug(it) }
                }
            })
    }
}