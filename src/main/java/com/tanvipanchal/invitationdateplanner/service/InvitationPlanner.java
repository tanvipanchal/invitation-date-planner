package com.tanvipanchal.invitationdateplanner.service;

import com.tanvipanchal.invitationdateplanner.model.Countries;
import com.tanvipanchal.invitationdateplanner.model.Country;
import com.tanvipanchal.invitationdateplanner.model.Partner;
import com.tanvipanchal.invitationdateplanner.model.Partners;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InvitationPlanner {

    public Countries getInvitationPlanPerCountry(Partners partners) {

        Map<String, Map<String, Integer>> countryMapWithDatesCount = getCountryMapWithDatesCount(partners);
        return getInvitationPlanForEachCountry(partners, countryMapWithDatesCount);

    }

    /** Create a map to store date availability counts for each country
     *
     * @param partners
     * @return A map with date availability counts for each country
     */
    public Map<String, Map<String, Integer>> getCountryMapWithDatesCount(Partners partners){
            List<Partner> listOfPartners = partners.getPartners();

            Map<String, Map<String, Integer>> countryDates = new HashMap<>();
            if (listOfPartners != null && !listOfPartners.isEmpty()) {
                String partnerCountry = null;

                for (Partner partner : listOfPartners) {
                    partnerCountry = partner.getCountry();
                    List<String> partnerAvailableDates = partner.getAvailableDates();

                    for (String date : partnerAvailableDates) {
                        countryDates.computeIfAbsent(partnerCountry, k -> new HashMap<>());
                        countryDates.get(partnerCountry).merge(date,1, Integer::sum);

                    }
                }

                return countryDates;
            }
            else{
                return null;
            }
        }

        public  Countries getInvitationPlanForEachCountry(Partners partners, Map<String, Map<String, Integer>> countryDates) {

            List<Map<String, Integer>> sortedDatesOfCountry = new ArrayList<>();
            Countries countries = new Countries(new ArrayList<>());

            for (Map.Entry<String, Map<String, Integer>> mapEntry : countryDates.entrySet()) {
                String currentCountry = mapEntry.getKey();
                Map<String, Integer> currentCountryDatesCountMap = mapEntry.getValue();

                List<Map.Entry<String, Integer>> sortedDates = new ArrayList<>(currentCountryDatesCountMap.entrySet());
                sortedDates.sort((a, b) -> {
                    int compare = b.getValue().compareTo(a.getValue());
                    if (compare == 0) {
                        return a.getKey().compareTo(b.getKey());
                    }
                    return compare;
                });

                String bestDate = null;
                List<String> attendees = new ArrayList<>();

                if (sortedDates.size() >= 2) {
                    bestDate = sortedDates.get(0).getKey();
                    for(Partner p : partnersToAttend(partners, currentCountry, bestDate)){
                        attendees.add(p.getEmail());
                    }
                }

                Country c = new Country();
                c.setName(currentCountry);
                c.setAttendees(attendees);
                c.setStartDate(bestDate);
                c.setAttendeeCount(attendees.size());
                countries.getCountries().add(c);
            }

            return countries;
        }

        public List<Partner> partnersToAttend(Partners partners, String country, String bestDate ){
                List<Partner> attendees = new ArrayList<>();

                for(Partner partner : partners.getPartners()){
                    if(partner.getCountry().equalsIgnoreCase(country) && partner.getAvailableDates().contains(bestDate)){
                        attendees.add(partner);
                    }
                }
                return attendees;
            }
}
