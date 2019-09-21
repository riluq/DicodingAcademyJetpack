package com.riluq.dicodingacademyjetpack.utils

import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import org.mockito.stubbing.Answer
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.invocation.InvocationOnMock


class PagedListUtil {
    companion object {
        fun <T> mockPagedList(list: List<T>): PagedList<T> {
            val pagedList: PagedList<T> = mock()
            val answer: Answer<T> = Answer<T> { invocation ->
                val index = invocation.arguments[0] as Int
                list[index]
            }

            `when`(pagedList[anyInt()]).thenAnswer(answer)
            `when`(pagedList.size).thenReturn(list.size)

            return pagedList
        }
    }
}