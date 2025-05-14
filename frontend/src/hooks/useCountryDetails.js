import {useEffect, useState} from "react";
import countryRepository from "../repository/countryRepository.js";

const useAuthorDetails = (id) => {
    const [state, setState] = useState({
        "country": null,
    });

    useEffect(() => {
        countryRepository
            .findById(id)
            .then((response) => {
                setState(prevState => ({...prevState, "country": response.data}));
            })
            .catch((error) => console.log(error));
    }, [id]);

    return state;
};

export default useAuthorDetails;
