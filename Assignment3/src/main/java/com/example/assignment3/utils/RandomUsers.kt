package com.example.assignment3.utils

import com.example.assignment3.model.room_db.entites.User
import kotlin.random.Random

//Random Functions
fun generateRandomAlphanumeric(length: Int, random: Random): String {
    val alphanumericChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { alphanumericChars[random.nextInt(alphanumericChars.size)] }
        .joinToString("")
}

fun generateRandomAlphabetical(minLength: Int, maxLength: Int, random: Random): String {
    val alphabetChars = ('a'..'z') + ('A'..'Z')
    val length = random.nextInt(minLength, maxLength + 1)
    return (1..length)
        .map { alphabetChars[random.nextInt(alphabetChars.size)] }
        .joinToString("")
}

fun generateRandomEmail(random: Random): String {
    val username = generateRandomAlphanumeric(5, random)
    val domain = generateRandomAlphabetical(4, 4, random)
    return "$username@$domain.com"
}

// Get Random 5 Users
public fun getRandomUsersList(): MutableList<User> {
    val userList = mutableListOf<User>()
    val random = Random.Default
    repeat(5) {

        val userId = random.nextLong(1_000_000_00L, 9_999_999_99L)
        val userName = generateRandomAlphanumeric(6, random)
        val fullName = generateRandomAlphabetical(9, 9, random)
        val email = generateRandomEmail(random)

        val user = User(userId, userName, fullName, email )

        userList.add(user)
    }
    return userList
}
// Get Random 1 User
public fun getRandomUser(): User {
    val random = Random.Default
    val user:User

    val userId = random.nextLong(1_000_000_00L, 9_999_999_99L)
    val userName = generateRandomAlphanumeric(6, random)
    val fullName = "${userName} ${generateRandomAlphabetical(1, 5, random)}"
    val email = generateRandomEmail(random)

    user = User(userId, userName, fullName, email )

    return user
}