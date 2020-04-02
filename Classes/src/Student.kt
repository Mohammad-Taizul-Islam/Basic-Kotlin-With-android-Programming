class Student(val name:String ="Student",var age:Double=99.0) {

    val stuName:String
    var stuAge:Double ?

    init {
        if (name=="Student")
        {
            stuName="NA"
            stuAge=0.0

        }else{

            stuName=name.toUpperCase()
            stuAge=age
        }

        println("Student name : $stuName")
        println("Student Age : $stuAge")
    }


}