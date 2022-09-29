import axios from 'axios';

const getCompanies = async () => {

    let res = await axios.get("http://localhost:8001/company/v1/")
    .then(response => {
        return response
    })
    .catch(error => {
        console.log(error)
        throw error
    })

    return res;
}

const companyApi = { getCompanies };

export default companyApi;
