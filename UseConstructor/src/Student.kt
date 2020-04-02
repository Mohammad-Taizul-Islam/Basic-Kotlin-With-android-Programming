class Student {
    var stuName : String ?
    val stuAge : Double

    constructor(name :String ,age :Double)
    {
        stuName=name;
        stuAge=age

        println("First constructor with two param ")
        println("First Name : $stuName")
        println("First Age : $stuAge")
    }
    constructor(name : String) : this(name,0.0)
    {
        stuName=name
        println("Second constructor with two param ")
        println("Sec Name : $stuName")
        println("Sec Age : $stuAge")
    }
}