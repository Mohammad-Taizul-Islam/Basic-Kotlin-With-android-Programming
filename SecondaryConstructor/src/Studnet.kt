class Student {

    constructor(name: String) :this(name,0)
    {
        println("Secondary constructor with one param")
    }
    constructor(name :String, age :Int)
    {
        println("Secondary constructor with two param ")
        println("Student Name : $name")
        println("Student Age : $age")
    }
}