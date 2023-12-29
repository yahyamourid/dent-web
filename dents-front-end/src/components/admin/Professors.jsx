
import Sidebar from './../Sidebar'
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Logout from '../auth/Logout';
const Professors = () => {
  const [user, setuser] = useState([]);
  const port = import.meta.env.VITE_PORT_SPRING;

  const fetchapi = () => {
    axios.get(`http://localhost:${port}/api/users/allProfessor`)
    .then(response => {
      setuser(response.data); 
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
  };
  

  useEffect(() => {
    fetchapi();
  }, []); 
  const handleActivateToggle = (userId) => {
    // Mettez à jour l'état localement
    setuser((prevUsers) =>
      prevUsers.map((user) =>
        user.id === userId ? { ...user, activate: !user.activate } : user
      )
    );

    axios
      .post(`http://localhost:${port}/api/users/activate/${userId}`)
      .then((response) => {
        // Mise à jour réussie
      })
      .catch((error) => {
        console.error('Error updating activate status:', error);
      
        setuser((prevUsers) =>
          prevUsers.map((user) =>
            user.id === userId ? { ...user, activate: !user.activate } : user
          )
        );
      });
  };







  return (
   
   <>
   
   <div class="mx-20 py-20">
  <div class="-m-1.5 overflow-x-auto">
    <div class="p-1.5 min-w-full inline-block align-middle">
      <div class="border rounded-lg overflow-hidden dark:border-gray-700">
        <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
          <thead class="bg-gray-50 dark:bg-gray-700">
            <tr>
              <th scope="col" class="px-6 py-3 text-start text-xs font-medium text-gray-500 uppercase dark:text-gray-400">FirstName </th>
              <th scope="col" class="px-6 py-3 text-start text-xs font-medium text-gray-500 uppercase dark:text-gray-400">LastaName</th>
              <th scope="col" class="px-6 py-3 text-start text-xs font-medium text-gray-500 uppercase dark:text-gray-400">@userName </th>
              <th scope="col" class="px-6 py-3 text-start text-xs font-medium text-gray-500 uppercase dark:text-gray-400">Email </th>
              <th scope="col" class="px-6 py-3 text-start text-xs font-medium text-gray-500 uppercase dark:text-gray-400">IsActivate </th>
              <th scope="col" class="px-6 py-3 text-end text-xs font-medium text-gray-500 uppercase dark:text-gray-400">Action</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
            {user.map((item)=>(
               <tr key={item.id}>
               <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-800 dark:text-gray-200">{item.firstName}</td>
               <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 dark:text-gray-200">{item.lastName}</td>
               <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 dark:text-gray-200">{item.userName}</td>
               <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 dark:text-gray-200">{item.email}</td>
               <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800 dark:text-gray-200">
  <input
    type="checkbox"
    id={`activate-checkbox-${item.id}`}
    className="relative w-[3.25rem] h-7 p-px bg-gray-100 border-transparent text-transparent rounded-full cursor-pointer transition-colors ease-in-out duration-200 focus:ring-blue-600 disabled:opacity-50 disabled:pointer-events-none checked:bg-none checked:text-blue-600 checked:border-blue-600 focus:checked:border-blue-600 dark:bg-gray-800 dark:border-gray-700 dark:checked:bg-blue-500 dark:checked:border-blue-500 dark:focus:ring-offset-gray-600 before:inline-block before:w-6 before:h-6 before:bg-white checked:before:bg-blue-200 before:translate-x-0 checked:before:translate-x-full before:rounded-full before:shadow before:transform before:ring-0 before:transition before:ease-in-out before:duration-200 dark:before:bg-gray-400 dark:checked:before:bg-blue-200"
    checked={item.activate}
    onChange={() => handleActivateToggle(item.id)} 
  />
</td>
               <td class="px-6 py-4 whitespace-nowrap text-end text-sm font-medium">
                 <button type="button" class="inline-flex items-center gap-x-2 text-sm font-semibold rounded-lg border border-transparent text-blue-600 hover:text-blue-800 disabled:opacity-50 disabled:pointer-events-none dark:text-blue-500 dark:hover:text-blue-400 dark:focus:outline-none dark:focus:ring-1 dark:focus:ring-gray-600">Delete</button>
               </td>
             </tr>

            ))}
           

          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
   </>
  )
}

export default Professors
