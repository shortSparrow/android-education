fun main(args: Array<String>) {
//    var mayArray = arrayOf(1,2,3);
//    var mayList = listOf(1,2,3);
//
//    var mutableList = mutableListOf<Int>(1,2,3)
//    mutableList.add(4);
//    println(mutableList)
//
//    var mutableList2 = mutableListOf(1,2,3, "Hi", false)
//    println(mutableList2)
//
//    var numbers = 10 downTo 0
//    println(numbers) // 10 downTo 0 step 1
//    val numberList = numbers.toList()
//    println(numberList) // [10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
//
//    var numbers2 = 0..10
//    println(numbers2) // 0..10
//
//
//
//    val map = mapOf("a" to 11, "b" to 22)
//    println(map) // {a=11, b=22}
//    println(map["a"]) // 11
//
//    val mutableMap = mutableMapOf("a" to 11, "b" to 22)
//    mutableMap["c"] = 4
//
//
//    val set = mutableSetOf<Int>(1,2,3) // contains only unique items
//    set.add(4)
//    set.add(1)
//    println(set) // [1, 2, 3, 4]



//    // functions
//
//    fun someFunction(vararg numbers: Int) { // vararg like rest operator
//        println(numbers.size) // 5
//
//        for(number in numbers) println(number) // 1-5
//    }
//
//    someFunction(1,2,3,4,5)


//    class

    class Person {
        var age = 0
        var isMarried = false
        var firstName: String
        var lastName: String

        constructor() {
            this.firstName = "John"
            this.lastName = "Doe"
        }

        constructor(firstName: String, lastName: String) {
            this.firstName = firstName
            this.lastName = lastName
        }
    }

    var Jack = Person()
    Jack.age = 23
    println(Jack.age) // 23
    println(Jack.firstName) // John

    var Nick = Person("Nick", "Patterson")
    println(Nick.age) // 23
    println(Nick.firstName) // Nick


    // short constructor
    class User (firstName: String, lastName: String, yearOfBirth:Int){
        var firstName = firstName
        var lastName = lastName
        var age = 0

        init {
            age = 2022 -yearOfBirth
        }

        fun getGullName():String {
            return "$firstName $lastName"
        }

        fun sayHello():String = "Hi" // function short variant
    }

    val user1 = User("Senya", "Volkov", 1945)
    println(user1.age) // 77
    println(user1.getGullName()) //Senya Volkov
    println(user1.sayHello()) // Hi
}