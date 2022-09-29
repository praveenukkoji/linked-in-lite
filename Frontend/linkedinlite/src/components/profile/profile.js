import { useState, useEffect } from 'react';
import userApi from '../../services/user';
import companyApi from '../../services/company';
import jobApi from '../../services/job';
import Job from '../../components/job/job';

import './profile.css';

const Profile = () => {

	var userId = localStorage.getItem("userId");

	const [userDetails, setUserDetails] = useState([]);
	const [companyList, setCompanyList] = useState([]);
	const [appliedJobList, setAppliedJobList] = useState([]);

	useEffect(() => {
		userApi.getUser(userId).then(response => {
			setUserDetails(response.data.payload);
			console.log("profile: " + response.data.message);
		});
		companyApi.getCompanies().then(response => {
			setCompanyList(response.data.payload);
			console.log("profile: " + response.data.message);
		});
	}, []);

	function appliedJobs(event) {
        event.preventDefault();
		console.log("profile: applied jobs");

		userApi.getUser(userId).then(response => {
			setUserDetails(response.data.payload);
			console.log("profile: " + response.data.message);

			let appliedJobIds = userDetails.appliedJobIds;

			jobApi.getAppiledJobs(appliedJobIds).then(response => {
				setAppliedJobList(response.data.payload);
				console.log("profile: " + response.data.message);
			});
		});
	}

	function editAccount(event) {
        event.preventDefault();
		console.log("profile: edit account");
	}

	function saveChanges(event) {
        event.preventDefault();
		
		let userId = localStorage.getItem("userId");
		let firstName = event.target[0].value; 
        let lastName = event.target[1].value; 
        let email = event.target[2].value; 
        let dob = event.target[3].value; 
        let skills = event.target[4].value; 
        let experienceYears = event.target[5].value; 
        let experienceMonths = event.target[6].value; 
        let currentCtcInLakhs = event.target[7].value; 
        let companyId = event.target[8].value;

		// skills can be empty
        if(firstName === "" || lastName === "" || email === "" || dob === "" || experienceYears === "" || experienceMonths === "" 
            || currentCtcInLakhs === "" || companyId === "") {
                alert("profile: invalid credentials");
                return;
        }

		userApi.updateUser(userId, firstName, lastName, email, dob, skills, experienceYears, experienceMonths, currentCtcInLakhs, companyId)
		.then(response => {
			alert("profile: " + response.data.message);
		});

		window.location.reload(false);
	}

	function logout(event) {
        event.preventDefault();
		localStorage.removeItem("userId");
		alert("profile: user logout");
	}

	function deleteAccount(event) {
        event.preventDefault();
		let userId = localStorage.getItem("userId");
		userApi.deleteUser(userId).then(response => {
			alert("profile: delete account");
			logout(event);
		});
	}

	return (
		<div className="profile-root">
			{
				!userDetails ? <div style={{textAlign: 'center', padding: '1em'}}>Loading...</div> :
				<div className="profile-container">
					<div className="card custom-card">
						<div className='profile-pic-container'>
							<img src="prem.png" className="card-img-top profile-pic" alt="" />
						</div>
						<div className="profile-card-body">
							<h4 className="card-title" style={{ textAlign: 'center' }}>{userDetails.firstName + " " + userDetails.lastName}</h4>
							<div className="profile-details">
								<div><strong>Email : </strong>{userDetails.email}</div>
								<div><strong>Date of Birth : </strong>{userDetails.dob}</div>
								<div><strong>Experience : </strong> {userDetails.experienceYears} yrs <strong>&</strong> {userDetails.experienceMonths} mon </div>
								<div><strong>Current CTC : </strong> {userDetails.currentCtcInLakhs} LPA</div>
								<div>
									<strong>Current Organization : </strong>
									{ companyList.map((company, i) => { 
										if(company.companyId === userDetails.companyId) {
											return ( 
												<span key={i}>{ company.companyName }</span>
											)
										}}
									)}
								</div>
								<div>
									<strong>Skills : </strong>
									<div>
										<div className="profile-skills-container">
											{
												userDetails.skills ? 
												userDetails.skills.map((skill, i) => {
													return <p className="btn btn-primary profile-skills" key={i}>{skill}</p>
												}) : <p>None</p>
											}
										</div>
									</div>
								</div>
							</div>
							<div className="btn-align-center">
								<button type="button" className="btn btn-success profile-btns" data-bs-toggle="modal" data-bs-target="#applied-jobs" onClick={appliedJobs}>Applied Jobs</button><br/>
								<button type="button" className="btn btn-primary profile-btns" data-bs-toggle="modal" data-bs-target="#edit-account" onClick={editAccount}>Edit Account</button><br/>
								<button type="button" className="btn btn-danger profile-btns" data-bs-toggle="modal" data-bs-target="#delete-account">Delete Account</button><br/>
								<button type="button" className="btn btn-secondary profile-btns" onClick={logout}>Log Out</button>
							</div>
						</div>
					</div>

					{/* Applied Jobs Modal */}
					<div className="modal fade modal-lg" id="applied-jobs" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div className="modal-dialog">
							<div className="modal-content">
							<div className="modal-header">
								<h5 className="modal-title" id="exampleModalLabel">Applied Jobs</h5>
								<button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div className="modal-body profile-applied-jobs">
								{
									appliedJobList.map(job => {
										return <Job key={job.jobId} {...job} applyBtn={false}/>
									})
								}
							</div>
							</div>
						</div>
					</div>

					{/* Edit Account Modal */}
					<div className="modal fade modal-lg" id="edit-account" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div className="modal-dialog">
							<div className="modal-content">
								<div className="modal-header">
									<h5 className="modal-title" id="exampleModalLabel">Edit Account</h5>
									<button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								</div>
								<div className="modal-body">
									<form className="row g-3 signiup-form" onSubmit={saveChanges}>
										<div className="col-md-6">
											<label className="form-label">First Name</label>
											<input type="text" className="form-control" id="firstname" defaultValue={userDetails.firstName} required/>
										</div>
										<div className="col-md-6">
											<label className="form-label">Last Name</label>
											<input type="text" className="form-control" id="lastname" defaultValue={userDetails.lastName} required/>
										</div>
										<div className="col-md-6">
											<label className="form-label">Email</label>
											<input type="email" className="form-control" id="email" defaultValue={userDetails.email} required/>
										</div>
										<div className="col-md-6">
											<label className="form-label">Date of Birth</label>
											<input type="date" className="form-control" id="dob" defaultValue={userDetails.dob} required/>
										</div>
										<div className="col-12">
											<label className="form-label">Add Skills</label>
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
											<input type="number" className="form-control" id="expinyears" defaultValue={userDetails.experienceYears} required/>
										</div>
										<div className="col-md-4">
											<label className="form-label">Experience in Months</label>
											<input type="number" className="form-control" id="expinmonths" defaultValue={userDetails.experienceMonths} required/>
										</div>
										<div className="col-md-4">
											<label className="form-label">Current CTC</label>
											<input type="number" className="form-control" id="currctc" defaultValue={userDetails.currentCtcInLakhs} required/>
										</div>
										<div className="col-12">
											<label className="form-label">Current Organization</label>
											<select defaultValue="" id="currorganization" className="form-select">
												{ companyList.map(company => { 
													if(company.companyId === userDetails.companyId) {
														return ( 
															<option value={company.companyId} key={company.companyId}>{ company.companyName }</option>
														);
													}}
												)}
												{ companyList.map(company => { 
													if(company.companyId !== userDetails.companyId) {
														return ( 
															<option value={company.companyId} key={company.companyId}>{ company.companyName }</option>
														); 
													}}
												)}
											</select>
										</div>
										<div>
											<button type="submit" className="btn btn-success">Save changes</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

					{/* Delete Account Modal */}
					<div className="modal fade" id="delete-account" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div className="modal-dialog">
							<div className="modal-content">
							<div className="modal-header">
								<h5 className="modal-title" id="exampleModalLabel">Delete Account</h5>
								<button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div className="modal-body">
								<p>Are you sure you want to <strong>delete</strong> your account permanently ?</p>
							</div>
							<div className="modal-footer">
								<button type="button" className="btn btn-danger" onClick={deleteAccount} data-bs-dismiss="modal" aria-label="Close">Delete Account</button>
							</div>
							</div>
						</div>
					</div>
				</div>
			}
		</div>
	);
}

export default Profile;
