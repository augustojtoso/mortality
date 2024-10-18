import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CountryRow from './CountryRow';

const MortalityTable = () => {
  const currentYear = new Date().getFullYear();
  const [year, setYear] = useState(currentYear);
  const [data, setData] = useState({});
  const [editCountryCode, setEditCountryCode] = useState(null);
  const [editData, setEditData] = useState({});

  useEffect(() => {
    fetchData(year);
  }, [year]);

  const fetchData = async (year) => {
    try {
      const response = await axios.get(`/mortalityrates/years/${year}/countries`);
      console.log('Data fetched:', response);
      setData(response.data.mortalityRates);
    } catch (error) {
      if (error.response && error.response.status === 404) {
        console.warn('Data not found for the year:', year);
        setData({});
      } else {
        console.error('Error fetching data:', error);
      }
    }
  };

  const handleYearChange = (newYear) => {
    setYear(newYear);
  };

  const handleEdit = (countryCode) => {
    setEditCountryCode(countryCode);
    setEditData(data[countryCode]);
  };

  const handleConfirm = async () => {
    try {
      await axios.post(`/mortalityrates/years/${year}/countries/${editCountryCode}`, editData);
      fetchData(year);
    } catch (error) {
      console.error('Error updating data:', error);
    }
    setEditCountryCode(null);
  };

  const handleCancel = () => {
    setEditCountryCode(null);
    setEditData({});
  };

  const handleChange = (e) => {
    setEditData({ ...editData, [e.target.name]: e.target.value });
  };

  return (
    <div className="container mt-5">
      <div className="mb-3">
        <select className="form-select" onChange={(e) => handleYearChange(parseInt(e.target.value))} value={year}>
          <option value={2018}>2018</option>
          <option value={2019}>2019</option>
          <option value={2020}>2020</option>
          <option value={2021}>2021</option>
          <option value={2022}>2022</option>
          <option value={2023}>2023</option>
          <option value={2024}>2024</option>
        </select>
      </div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Country Code</th>
            <th scope="col">Male Mortality Rate</th>
            <th scope="col">Female Mortality Rate</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {Object.keys(data).map((countryCode) => (
            <CountryRow
              key={countryCode}
              countryCode={countryCode}
              mortalityRate={data[countryCode]}
              editCountryCode={editCountryCode}
              editData={editData}
              handleChange={handleChange}
              fetchData={fetchData}
              year={year}
            />
          ))}
          <tr>
            <td>
              <button className="btn btn-primary" onClick={() => handleEdit('new')}>+</button>
            </td>
            <td colSpan="2">No more data available for this year</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default MortalityTable;