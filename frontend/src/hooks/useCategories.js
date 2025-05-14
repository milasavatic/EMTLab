import {useEffect, useState} from "react";
import categoryRepository from "../repository/categoryRepository.js";

const useCategories = () => {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        categoryRepository
            .findAll()
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => console.log(error));
    }, []);

    return categories;
};

export default useCategories;
