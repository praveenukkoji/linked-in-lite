import axios from 'axios';

const loginUser = async (email, password) => {

    let body = {
        "email": email,
        "password": password
    }

    let res = await axios.post("http://localhost:8003/user/v1/login/", body)
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const getUser = async (userId) => {

    let res = await axios.get("http://localhost:8003/user/v1/" + userId + "/")
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const createUser = async (firstName, lastName, email, dob, skills, experienceYears, experienceMonths, currentCtcInLakhs, companyId) => {

    let body = {
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "appliedJobIds": [],
        "dob": dob,
        "skills": [skills],
        "experienceYears": experienceYears,
        "experienceMonths": experienceMonths,
        "currentCtcInLakhs": currentCtcInLakhs,
        "companyId": companyId
    }

    let res = await axios.post("http://localhost:8003/user/v1/", body)
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const updateUser = async (userId, firstName, lastName, email, dob, skills, experienceYears, experienceMonths, currentCtcInLakhs, companyId) => {
    
    let params = {
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "appliedJobIds": null,
        "dob": dob,
        "skills": skills,
        "experienceYears": experienceYears,
        "experienceMonths": experienceMonths,
        "currentCtcInLakhs": currentCtcInLakhs,
        "companyId": companyId
    }

    let res = await axios.put("http://localhost:8003/user/v1/" + userId + "/", null, { params: params })
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const deleteUser = async (userId) => {

    let res = await axios.delete("http://localhost:8003/user/v1/" + userId + "/")
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const applyJob = async (userId, jobId) => {

    let res = await axios.put("http://localhost:8003/user/v1/applyJob/" + userId + "/" + jobId + "/")
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const userApi = { loginUser, getUser, createUser, updateUser, deleteUser, applyJob };

export default userApi;
