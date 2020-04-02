fun main()
{

    var arrayint = Array<Int>(5)
    { readLine()!!.toInt() }
    sums(*arrayint )
    for(x in arrayint)
        println(x)


}
fun sums(vararg arrayint : Int )
{

}