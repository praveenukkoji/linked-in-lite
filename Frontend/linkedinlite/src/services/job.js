import axios from 'axios';

const getJobs = async () => {

    let res = await axios.get("http://localhost:8002/job/v1/")
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const getAppiledJobs = async (appliedJobIds) => {

    let params = {
        jobIds: appliedJobIds.join(',')
    }

    let res = await axios.get("http://localhost:8002/job/v1/multiple/", { params: params })
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const jobApi = { getJobs, getAppiledJobs };

export default jobApi;
