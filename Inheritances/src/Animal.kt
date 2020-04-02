open class Animal(color :String,age:Int) {
    init {
        println("Color is : $color")
        println("Age is : $age")
    }
    open fun woof()
    {
        println("Animal makes sounds")
    }
}