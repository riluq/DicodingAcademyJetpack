package com.riluq.dicodingacademyjetpack.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.riluq.dicodingacademyjetpack.data.source.remote.ApiResponse
import com.riluq.dicodingacademyjetpack.data.source.remote.StatusResponse
import com.riluq.dicodingacademyjetpack.utils.AppExecutors
import com.riluq.dicodingacademyjetpack.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    protected fun onFetchFailed() {
    }

    protected abstract fun loadFromDB(): LiveData<ResultType>?

    protected abstract fun shouldFetch(data: ResultType?): Boolean?

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    protected abstract fun saveCallResult(data: RequestType?)

    init {
        result.value = Resource.loading(null)

        val dbSource: LiveData<ResultType> = this.loadFromDB()!!

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)!!) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse: LiveData<ApiResponse<RequestType>> = createCall()!!

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.statusResponse) {
                StatusResponse.SUCCESS -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(response.body!!)

                        appExecutors.mainThread().execute {
                            result.addSource(loadFromDB()!!) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                }
                StatusResponse.EMPTY -> {
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDB()!!) { newData ->
                            result.value = Resource.success(newData)
                        }
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message!!, newData)
                    }
                }

            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}