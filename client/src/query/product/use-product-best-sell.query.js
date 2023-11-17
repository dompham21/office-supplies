import{ get } from "@utils/api/AxiosService";
import { API_ENDPOINTS } from '@utils/api/endpoints';
import {
QueryKey,
useInfiniteQuery,
UseInfiniteQueryOptions,
useQuery,
} from 'react-query';


const fetchProducts = async () => {    
    const response = await get(API_ENDPOINTS.PRODUCTS_BEST_SELL);
    const { data } = response;
    console.log(data)
    return {
        products: data.data
     };
};



const useProductBestSellQuery = (  ) => {
    return useQuery(
        [API_ENDPOINTS.PRODUCTS_BEST_SELL],
        () => fetchProducts(),
        {   
            retry: false,
            keepPreviousData: true,
            refetchOnWindowFocus: false,
        }
    );
};

export { useProductBestSellQuery, fetchProducts };

