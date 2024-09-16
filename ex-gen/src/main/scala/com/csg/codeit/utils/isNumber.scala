package com.csg.codeit.utils

object isNumber {
  def apply(v: Any): Boolean = {
    v.isInstanceOf[Double] || v.isInstanceOf[Int]
  }
}
