program_INCLUDE  += src/include
program_CXX_OBJS += ${program_CXX_SRC:.cpp=.o}
program_NAME    := BinaryTreeProblems

rwildcard=$(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2) $(filter $(subst *,%,$2),$d))
program_CXX_SRC := $(call rwildcard,,*.cpp)

CPPFLAGS += $(foreach includedir,$(program_INCLUDE),-I$(includedir))
#CXXFLAGS += -03
CXXFLAGS += -std=c++0x
#CFLAGS += 

all: $(program_NAME)

$(program_NAME): $(program_CXX_OBJS)
	$(LINK.cc) $(program_CXX_OBJS) -o $(program_NAME)
print:
	@echo $(program_CXX_SRC)
