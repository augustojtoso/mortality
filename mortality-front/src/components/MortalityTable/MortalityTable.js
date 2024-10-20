import React, { useState, useEffect } from 'react';
import NewCountryRateInput from '../addNewCountryMortalityRate/NewCountryRateInput';
import { fetchMortalityRates, saveMortalityRate } from '../../facade/apiFacade';
import CountryRow from './CountryRow';

const MortalityTable = () => {
  const lastYear = new Date().getFullYear() - 1;
  const years = Array.from({ length: 10 }, (_, i) => lastYear - i);
  const [year, setYear] = useState(lastYear);
  const [data, setData] = useState({});
  const [addingNew, setAddingNew] = useState(false);

  useEffect(() => {fetchData(year);}, [year]);

  const fetchData = async (year) => {
    try {
      const mortalityRates = await fetchMortalityRates(year);
      setData(mortalityRates);
    } catch (error) {
      setData({});
    }
  };


  const handleSave = async (newRow) => {
    const { countryCode, ...postData } = newRow;
    const updatedData = await saveMortalityRate(year, countryCode, postData);
    setData(updatedData);
    setAddingNew(false);
  };


  return (
    <div className="container mt-5">
      <div className="mb-3">
        <select className="form-select" onChange={(e) => setYear(parseInt(e.target.value))} value={year}>
          {years.map((yearOption) => (
            <option key={yearOption} value={yearOption}>{yearOption}</option>
          ))}
        </select>
      </div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th>Country Code</th>
            <th>Male Mortality Rate</th>
            <th>Female Mortality Rate</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {Object.entries(data).map(([countryCode, mortalityRate]) => (
            <CountryRow
              key={countryCode}
              countryCode={countryCode}
              mortalityRate={mortalityRate}
              onConfirm={handleSave}
            />
          ))}
          {addingNew && (
            <NewCountryRateInput
              onConfirm={handleSave}
              onCancel={() => setAddingNew(false)}
              allCountries={Object.keys(data)}
            />
          )}
          <tr>
            <td>
              <button className="btn btn-primary" onClick={() => setAddingNew(true)} disabled={addingNew}>+</button>
            </td>
            <td colSpan="2">No more data available for this year</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default MortalityTable;