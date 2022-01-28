package com.gbjm.core.architecture.mapper

import java.util.ArrayList

/**
 * Mapper provides a basic structure to map model objects to resulting UI objects
 *
 * @param E the model object type
 * @param T the resulting object type
 *
 */
abstract class Mapper<in E, T> {
    abstract fun mapFrom(from: E): T
}

/**
 * ListMapper provides support for mapping a collection of model objects
 *
 * @param I the model object type
 * @param O the resulting object type
 */
abstract class ListMapper<I, O> : Mapper<List<I>, List<O>>() {

    /**
     * The inner mapper used to map the collection
     */
    abstract val mapper: Mapper<I, O>

    /**
     * Invokes the collection mapping
     *
     * @param from the collection source of objects
     * @return the resulting collection of objects
     */
    override fun mapFrom(from: List<I>): List<O> {
        val results = ArrayList<O>()
        for (result in from) {
            results.add(mapper.mapFrom(result))
        }
        return results
    }
}