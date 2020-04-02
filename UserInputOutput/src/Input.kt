import java.util.*

fun main()
{
    println("Enter a String \n")
    var str : String? = readLine()
    println(str)
    println("Enter a Integer : \n")
   // val userInputInt = readLine()!!.toInt()

   // println(userInputInt)

    var i :Int=Integer.valueOf(readLine())
    println("Value pf Integer $i")
    var scanner : Scanner ? =Scanner(System.`in`)

    println("Enter a Double number : \n")
    var d : Double ? = scanner?.nextDouble();
    println("Value of double $d")
    println("Enter a Integer Number :\n")
    val int =scanner ?.nextInt()
    println("Value of integer $int")




}