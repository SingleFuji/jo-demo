def a = 3
def b = "33"
b.each {
    it-> println(it)
        println("it is ${it}")
}

def printStr(head, tail){
    println "head $head"
    println "tail $tail"
}