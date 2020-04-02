open class Parent {
    //by default public
    val num :Int =190
    //protected
    open protected  val  name="Mali"
    //private
    private var lover="Sathi"
    //protected function
    open protected fun demo()
    {
        println("Who are you : $lover")
    }
    internal var job: Boolean=false

}