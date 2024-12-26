package com.kyryll.costtracking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyryll.costtracking.data.local.entity.TransactionEntity
import com.kyryll.costtracking.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, TransactionEntity::class],
    version = 3
)
abstract class CoinDatabase: RoomDatabase() {
    abstract val dao: CoinDao
}