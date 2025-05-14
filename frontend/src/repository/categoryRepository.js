import axiosInstance from "../axios/axios.js";

const categoryRepository = {
    findAll: async () => {
        return await axiosInstance.get("/categories");
    },
};

export default categoryRepository;
