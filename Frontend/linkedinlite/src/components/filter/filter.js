import { useState, useEffect } from 'react';
import companyApi from '../../services/company';
import './filter.css';

function Filter() {
    const [companyList, setCompanyList] = useState([])

	useEffect(() => {
		companyApi.getCompanies().then(response => {
			setCompanyList(response.data.payload);
			console.log("filter: " + response.data.message);
		})
	}, []);

    function applyfilters(event) {
        event.preventDefault();

        alert("Coming Soon.")

        // let companyId = event.target[0].value;
        // let skills = event.target[1].value;
        // let experienceYears = event.target[2].value;

        // if(companyId === "" && skills === "" && experienceYears === "") {
        //     alert("filter: invalid filters");
        //     return;
        // }

        // alert("filter: \n" + companyId + "\n" + skills + "\n" + experienceYears);
        // api call
    }

	return (
		<div className="filter-root">
            <div className="filter-container">
                <form className="row g-3" onSubmit={applyfilters}>
                    <div className="col-12">
                        <label className="form-label">Comapny</label>
                        <select defaultValue="" id="company" className="form-select">
                            <option value={""}>Select</option>
                            { companyList.map(company => { 
                                    return ( 
                                        <option value={company.companyId} key={company.companyId}>{ company.companyName }</option> 
                                    ); 
                                }
                            )};
                        </select>
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
                    <div className="col-12">
                        <label className="form-label">Experience in Years</label>
                        <input type="number" className="form-control" id="expinyears" defaultValue={""}/>
                    </div>
                    <div className="btn-align-center">
                        <button type="submit" className="btn btn-primary">Apply Filters</button>
                    </div>
                </form>
            </div>
		</div>
	);
}

export default Filter;
