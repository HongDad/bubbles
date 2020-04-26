package data.structures

import scala.collection.mutable.ArrayBuffer

class MyCircularQueue(_k: Int) {

  /** Initialize your data structure here. Set the size of the queue to be k. */
  private   var  data= new ArrayBuffer[Int](_k)
  private   var  p_start = 0
  private   var  p_end = 0
  /** Insert an element into the circular queue. Return true if the operation is successful. */
  def enQueue(value: Int): Boolean = {
    if (isFull()){
      return  false
    }else{
      data += value
      p_end  = p_end +1
      return true
    }
  }

  /** Delete an element from the circular queue. Return true if the operation is successful. */
  def deQueue(): Boolean = {
     if (isEmpty()){
       return false
     }else{
       data.drop(p_start)
       p_start = p_start + 1
       return true
     }
  }

  /** Get the front item from the queue. */
  def Front(): Int = {
     data(p_start)
  }

  /** Get the last item from the queue. */
  def Rear(): Int = {
    data(p_end)
  }

  /** Checks whether the circular queue is empty or not. */
  def isEmpty(): Boolean = {
    return  p_end - p_start == 0
  }

  /** Checks whether the circular queue is full or not. */
  def isFull(): Boolean = {
      return p_end - p_start == -1 || p_start - p_end == 6
  }
  def  forPrint()={
     data
  }
}

/**
  * Your MyCircularQueue object will be instantiated and called as such:
  * var obj = new MyCircularQueue(k)
  * var param_1 = obj.enQueue(value)
  * var param_2 = obj.deQueue()
  * var param_3 = obj.Front()
  * var param_4 = obj.Rear()
  * var param_5 = obj.isEmpty()
  * var param_6 = obj.isFull()
  */
object MyCircularQueue{
  def main(args: Array[String]): Unit = {
      val k = 7
      val value = 1
      var obj = new MyCircularQueue(k)
      var param_1 = obj.enQueue(value)
       obj.enQueue(2)
       obj.enQueue(3)
      var param_2 = obj.deQueue()
      var param_3 = obj.Front()
      var param_4 = obj.Rear()
      var param_5 = obj.isEmpty()
      var param_6 = obj.isFull()
      obj.forPrint().foreach(println)
    println(param_1,
      param_2,
      param_3,
      param_4,
      param_5,
      param_6)

  }
}


