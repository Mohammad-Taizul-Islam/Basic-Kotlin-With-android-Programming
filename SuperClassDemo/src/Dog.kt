class Dog :Animal()
{
    override var color:String="Black"
    override fun sound() {
        super.sound()
        println("Demo function of Dog class")
    }

    fun getColor()
    {
        println("Color from Animal class is : "+super.color)
    }
}