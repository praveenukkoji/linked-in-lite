import { useState, useEffect } from 'react';
import companyApi from '../../services/company';
import userApi from '../../services/user';
import './signup.css';

function SignUp() {
    const [companyList, setCompanyList] = useState([])

	useEffect(() => {
		companyApi.getCompanies().then(response => {
			setCompanyList(response.data.payload);
			console.log("signup: " + response.data.message);
		})
	}, []);

    function createUser(event){
        event.preventDefault();
        
        let firstName = event.target[0].value; 
        let lastName = event.target[1].value; 
        let email = event.target[2].value; 
        let dob = event.target[3].value; 
        let skills = event.target[4].value; 
        let experienceYears = event.target[5].value; 
        let experienceMonths = event.target[6].value; 
        let currentCtcInLakhs = event.target[7].value; 
        let companyId = event.target[8].value;

        if(firstName === "" || lastName === "" || email === "" || dob === "" || skills === "" || experienceYears === "" || experienceMonths === "" 
            || currentCtcInLakhs === "" || companyId === ""){
                alert("signup: invalid credentials");
                return;
        }

        userApi.createUser(firstName, lastName, email, dob, skills, experienceYears, experienceMonths, currentCtcInLakhs, companyId)
        .then(response => {
            alert("signup: " + response.data.message);
        })
    }

	return (
		<div className='signup-root'>
			<form className="row g-3 signiup-form" onSubmit={createUser}>
                <div className="col-md-6">
                    <label className="form-label">First Name</label>
                    <input type="text" className="form-control" id="firstname" required/>
                </div>
                <div className="col-md-6">
                    <label className="form-label">Last Name</label>
                    <input type="text" className="form-control" id="lastname" required/>
                </div>
                <div className="col-md-6">
                    <label className="form-label">Email</label>
                    <input type="email" className="form-control" id="email" required/>
                </div>
                <div className="col-md-6">
                    <label className="form-label">Date of Birth</label>
                    <input type="date" className="form-control" id="dob" required/>
                </div>
                <div className="col-12">
                    <label className="form-label">Skills</label>
                    <select defaultValue="" id="skills" className="form-select">
                        <option value={""}>Select</option>
                        <option>Java</option>
                        <option>Python</option>
                        <option>DBMS</option>
                        <option>Postgres SQL</option>
                        <option>React Js</option>
                        <option>Angular Js</option>
                        <option>Spring Boot</option>
                        <option>Django</option>
                    </select>
                </div>
                <div className="col-md-4">
                    <label className="form-label">Experience in Years</label>
                    <input type="number" className="form-control" id="expinyears" defaultValue={0} required/>
                </div>
                <div className="col-md-4">
                    <label className="form-label">Experience in Months</label>
                    <input type="number" className="form-control" id="expinmonths" defaultValue={0} required/>
                </div>
                <div className="col-md-4">
                    <label className="form-label">Current CTC in Lakhs</label>
                    <input type="number" className="form-control" id="currctc" defaultValue={0} required/>
                </div>
                <div className="col-12">
                    <label className="form-label">Current Organization</label>
                    <select defaultValue="" id="currorganization" className="form-select">
                        <option value={""}>Select</option>
                        { companyList.map(company => { 
                                return ( 
                                    <option value={company.companyId} key={company.companyId}>{ company.companyName }</option> 
                                ); 
                            }
                        )}
                    </select>
                </div>
                <div className="btn-align-center">
                    <button type="submit" className="btn btn-primary">Sign Up</button>
                </div>
            </form>
		</div>
	);
}

export default SignUp;
