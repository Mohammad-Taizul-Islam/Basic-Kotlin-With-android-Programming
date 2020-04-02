class Student : College{

    constructor(name: String ,age :Int) :super(name, age)
    {

        println("child class constructor")
        println("Student Name: $name")
        println("Student Age: $age")
    }

    fun getCollegeName()
    {
        println("College Name :"+" Govt. A.H. College, Bogura")
    }
}