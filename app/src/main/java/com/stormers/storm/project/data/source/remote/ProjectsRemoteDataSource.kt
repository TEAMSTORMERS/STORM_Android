package com.stormers.storm.project.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.data.source.ProjectsDataSource
import com.stormers.storm.project.model.ProjectDetailInfo
import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.project.network.response.ResponseProjectPreviews
import com.stormers.storm.project.network.response.ResponseProjectDetialInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProjectsRemoteDataSource : ProjectsDataSource {

    private const val TAG = "ProjectsRemoteDataSource"

    private val requestProject: RequestProject by lazy { RetrofitClient.create(RequestProject::class.java) }

    @SuppressLint("LongLogTag")
    override fun getProjectPreviews(
        userIdx: Int,
        callback: ProjectsDataSource.LoadProjectsCallback<ProjectPreviewModel>
    ) {
        Log.d(TAG, "getProjectPreviews: userIdx : $userIdx")
        requestProject.getProjectPreviews(userIdx).enqueue(object : Callback<ResponseProjectPreviews> {

            override fun onFailure(call: Call<ResponseProjectPreviews>, t: Throwable) {
                Log.d(TAG, "getProjectPreviews: Fail, ${t.message}")
            }

            override fun onResponse(
                call: Call<ResponseProjectPreviews>,
                response: Response<ResponseProjectPreviews>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "getProjectPreviews: Success")

                        if (response.body()!!.data.isEmpty()) {
                            callback.onDataNotAvailable()
                        } else {
                            callback.onProjectsLoaded(response.body()!!.data)
                        }
                    } else {
                        Log.d(TAG, "getProjectPreviews: Not Success, ${response.body()!!.message}")
                        callback.onDataNotAvailable()
                    }
                } else {
                    Log.d(TAG, "getProjectPreviews: Not Successful, ${response.message()}")
                    callback.onDataNotAvailable()
                }
            }
        })
    }

    @SuppressLint("LongLogTag")
    override fun getProjectDetailInfo(
        projectIdx: Int,
        callback: ProjectsDataSource.GetProjectCallback<ProjectDetailInfo>
    ) {
        Log.d(TAG, "getProjectDetailInfo: projectIdx: $projectIdx")

        requestProject.getProjectDetailInfo(projectIdx).enqueue(object: Callback<ResponseProjectDetialInfo> {

            override fun onFailure(call: Call<ResponseProjectDetialInfo>, t: Throwable) {
                Log.d(TAG, "getProjectDetailInfo: Fail, ${t.message}")
                callback.onDataNotAvailable()
            }

            override fun onResponse(
                call: Call<ResponseProjectDetialInfo>,
                response: Response<ResponseProjectDetialInfo>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "getProjectDetailInfo: Success")
                        callback.onProjectLoaded(response.body()!!.data)
                    } else {
                        Log.d(TAG, "getProjectDetailInfo: Not Success, ${response.body()!!.message}")
                        callback.onDataNotAvailable()
                    }
                } else {
                    Log.d(TAG, "getProjectDetailInfo: Not Successful, ${response.message()}")
                    callback.onDataNotAvailable()
                }
            }
        })
    }

    override fun saveProjectPreviews(projectPreviewModels: List<ProjectPreviewModel>) {
        //Not required
    }

    override fun saveProjectDetailInfo(projectDetailInfo: ProjectDetailInfo) {
        //Not required
    }
}